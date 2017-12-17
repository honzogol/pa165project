import {Component, Inject, OnInit} from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { Monster } from '../../entity.module';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";


@Component({
  selector: 'app-monsters',
  templateUrl: './monsters.component.html',
  styleUrls: ['./monsters.component.scss']
})
export class MonstersComponent implements OnInit {

  displayedColumns = ['id', 'name', 'agility', 'height', 'weight', 'detail', 'remove'];
  showMonsters: boolean = false;
  monsters: Monster[] = [];
  dataSource: MatTableDataSource<Monster>;
  cookie: boolean = false;
  isAdmin: boolean = false;

  constructor(private http: HttpClient,
              private cookieService: CookieService,
              private router: Router,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {}

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIsAdminCookie()
    this.loadMonsters();
  }

  checkIsAdminCookie(){
    if (this.cookieService.get('creatures-is_admin') == "true"){
      this.isAdmin = true;
      return;
    }
    this.displayedColumns = ['id', 'name', 'agility', 'height', 'weight', 'detail'];
    this.isAdmin = false;
  }

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
      this.router.navigate(['/login']);
    }
  }

  loadMonsters() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Monster[]>(this.config.apiEndpoint + '/pa165/rest/auth/monsters/', { withCredentials: true }).subscribe(
    data => {
      this.monsters = data;
      this.dataSource = new MatTableDataSource(this.monsters);
      console.log('Monsters loaded:\n' + data);
      this.showMonsters = true;
    },
    error => {
    });
  }

  removeMonster(id) {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.delete(this.config.apiEndpoint + '/pa165/rest/auth/monsters/' + id,  {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Removing monster with id: " + id +"was successful.");
        this.loadMonsters();
      },
      error => {
        console.log("Error during removing monster with id: " + id +".");
      });
  }
}

