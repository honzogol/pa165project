import {Component, Inject, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Monster, Weapon} from '../../entity.module';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog, MatTableDataSource} from "@angular/material";
import {CookieService} from "ngx-cookie-service";
import {AddMonstersComponent} from "../../add-monsters-dialog/add-monsters-dialog.component";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";
import {ErrorDialogComponent} from "../../error-dialog/error-dialog.component";

@Component({
  selector: 'app-weapon-detail',
  templateUrl: './weapon-detail.component.html',
  styleUrls: ['./weapon-detail.component.scss']
})
export class WeaponDetailComponent implements OnInit {

  displayedColumns = ['id', 'name', 'agility', 'weight', 'height', 'remove'];
  weaponId: number;
  showWeapon: boolean = false;
  weapon: Weapon;
  weaponType: string;
  appropriateMonsters: Monster[] = [];
  dataSource: MatTableDataSource<Monster>;

  isAdmin: boolean = false;

  cookie: boolean = false;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private cookieService: CookieService,
              private router: Router,
              private dialog: MatDialog,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {
    this.route.params.subscribe(res => this.weaponId = res.id);

  }

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    if (!this.cookie) {
      this.router.navigate(['/login']);
      this.dialog.open(ErrorDialogComponent, {
        width: '600px',
        data: ["User is not logged in."],
      });
      return;
    }
    this.checkIsAdminCookie();
    this.loadData();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      this.router.navigate(['/login']);
    }
  }

  checkIsAdminCookie(){
    if (this.cookieService.get('creatures-is_admin') == "true"){
      this.isAdmin = true;
      return;
    }
    this.isAdmin = false;
  }

  loadData(){
    this.showWeapon = false;
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Weapon>(this.config.apiEndpoint + '/pa165/rest/auth/weapons/' + this.weaponId, {withCredentials: true}).subscribe(
      data => {
        console.log('Weapon detail loaded:\n' + data);
        this.weapon = data;
        this.showWeapon = true;
        this.weaponType = data.type == null ? 'null' : data.type;
        this.appropriateMonsters = data.appropriateMonsters;
        this.dataSource = new MatTableDataSource(this.appropriateMonsters);
        this.showWeapon = true;
      });
  }

  showMonsters(){

    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();

    let dialogRef = this.dialog.open(AddMonstersComponent, {
      width: '600px',
      data: this.weapon,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("Dialog closed");
      this.loadData();
    });
  }

  removeAppropriateMonster(monsterId){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.put(this.config.apiEndpoint + '/pa165/rest/auth/weapons/' + this.weaponId + '/removeAppropriateMonster?monsterId='+ monsterId ,  null, {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Removing appropriate monster with id: " + monsterId + " to weapon with id: " + this.weaponId + "was successful.");
        this.loadData();
      }, error => {
        console.log("Error during removing appropriate monster with id: " + monsterId + " to weapon with id: " + this.weaponId + "was successful.");
      }
    )
  }

  updateWeapon(name, weaponType, range, magazineCapacity){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    var json = {"id":this.weaponId,"name": name,"type":weaponType, "range":range, "magazineCapacity":magazineCapacity};
    this.http.put(this.config.apiEndpoint + '/pa165/rest/auth/weapons/update/' + this.weaponId, json, {withCredentials: true}).subscribe(
      data => {
        console.log("Updating weapon with name: " + name + ", type: " + weaponType + ", range: "+ range + "and magazine capacity: " + magazineCapacity + "was successful.");
        this.loadData();
      }, error => {
        console.log("Error during updating weapon with name: " + name + ", type: " + weaponType + ", range: "+ range + "and magazine capacity: " + magazineCapacity + "was successful.");
      }
    )
  }
}
