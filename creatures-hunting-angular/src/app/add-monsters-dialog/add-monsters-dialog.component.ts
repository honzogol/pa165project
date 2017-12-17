import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource} from "@angular/material";
import {Monster, Weapon} from "../entity.module";
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import {ApplicationConfig, CONFIG_TOKEN} from "../app-config";

@Component({
  selector: 'app-error-dialog',
  templateUrl: './add-monsters-dialog.component.html',
  styleUrls: ['./add-monsters-dialog.component.scss']
})
export class AddMonstersComponent implements OnInit {

  dataSourceMonsters: MatTableDataSource<Monster>;
  displayedColumns = ['name', 'agility', 'weight', 'height', 'add'];
  monsterCandidates: Monster[] = [];
  cookie: boolean = false;
  weapon: Weapon;
  empty: boolean = true;
  showMonsters: boolean = false;

  constructor(public dialogRef: MatDialogRef<AddMonstersComponent>,
              private cookieService: CookieService,
              private router: Router,
              @Inject(MAT_DIALOG_DATA) public givenWeapon: Weapon,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig,
              private http: HttpClient) {
    this.weapon = givenWeapon;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit(): void {
    this.cookie = this.cookieService.check('creatures-token');
    this.loadData();
  }

  loadData() {
    this.showMonsters = false;
    this.http.get<Monster[]>(this.config.apiEndpoint + '/pa165/rest/auth/monsters/', {withCredentials: true}).subscribe(
      data => {
        this.monsterCandidates = data;
        let alreadyAdded = this.weapon.appropriateMonsters;
        this.monsterCandidates = this.monsterCandidates.filter(function(monster) {
          for (let weaponMonster of alreadyAdded) {
            if (monster.id == weaponMonster.id) {
              return false;
            }
          }
          return true;
        });
        console.log("Monsters size: " + this.monsterCandidates.length);
        this.dataSourceMonsters = new MatTableDataSource(this.monsterCandidates);
        this.empty = this.monsterCandidates.length == 0;
        this.showMonsters = true;
      });
  }

  addAppropriateMonster(monsterId){

    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.put(this.config.apiEndpoint + '/pa165/rest/auth/weapons/' + this.weapon.id + '/addAppropriateMonster?monsterId='+ monsterId ,  null, {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        this.filterAddedMonster(monsterId);
        this.loadData();
      }, error => {
        console.log(error);
      }
    )
  }

  filterAddedMonster(id : number) {
    for (let monster of this.monsterCandidates) {
      if (monster.id == id) {
        this.weapon.appropriateMonsters.push(monster);
      }
    }
    this.monsterCandidates = this.monsterCandidates
      .filter(monster => { return monster.id != id});
  }

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
      this.router.navigate(['/login']);
    }
  }
}
