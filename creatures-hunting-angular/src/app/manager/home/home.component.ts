import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Area, Monster, Weapon} from "../../entity.module";
import {MatTableDataSource} from "@angular/material";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import 'rxjs/add/operator/toPromise';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  mostEffectiveWeapons: Weapon[] = [];
  mostDangerousAreas: Area[] = [];
  mostWidespreadMonsters: Monster[] = [];

  dataWeapons : MatTableDataSource<Weapon>;
  dataAreas : MatTableDataSource<Area>;
  dataMonsters : MatTableDataSource<Monster>;

  columnsWeapons = ['id', 'name', 'type', 'range', 'magazineCapacity'];
  columnsAreas = ['id', 'name', 'type'];
  columnsMonsters = ['id', 'name', 'agility', 'height', 'weight'];

  showWeapons : boolean = false;
  showMonsters : boolean = false;
  showAreas : boolean = false;

  cookie: boolean = false;

  constructor(private http: HttpClient, private cookieService: CookieService , private router: Router) { }



  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.loadMostDangerousAreas();
    this.loadMostEffectiveWeapon();
    this.loadMostWidespreadMonsters();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
      this.router.navigate(['/login']);
    }
  }

  loadMostWidespreadMonsters(){
    this.cookie = this.cookieService.check('creatures-token');
    this.http.get<Monster[]>('http://localhost:8080/pa165/rest/auth/monsters/filter/mostWidespread', {withCredentials: true}).subscribe(
      data => {
        this.mostWidespreadMonsters = data;
        this.dataMonsters = new MatTableDataSource(this.mostWidespreadMonsters);
        console.log('Most widespread monsters loaded.\n' + data);
        this.showMonsters = true;
      },
      error => {
        console.log("Error during loading most widespread monsters.\n" + error);
      }
    );
    return null;
  }

  loadMostDangerousAreas(){
    this.cookie = this.cookieService.check('creatures-token');
    this.http.get<Area[]>('http://localhost:8080/pa165/rest/auth/areas/filter/mostDangerous', {withCredentials: true}).subscribe(
      data => {
        this.mostDangerousAreas = data;
        this.dataAreas = new MatTableDataSource(this.mostDangerousAreas);
        console.log('Most dangerous areas loaded.\n' + data);
        this.showAreas = true;
      },
      error => {
        console.log("Error during loading most dangerous areas.\n" + error);
      }
    );
    return null;
  }

  loadMostEffectiveWeapon(){
    this.cookie = this.cookieService.check('creatures-token');
    this.http.get<Weapon[]>('http://localhost:8080/pa165/rest/auth/weapons/filter/mostEffectiveWeapon', {withCredentials: true}).subscribe(
      data => {
        this.mostEffectiveWeapons = data;
        this.dataWeapons = new MatTableDataSource(this.mostEffectiveWeapons);
        console.log('Most effective weapons loaded.\n' + data);
        this.showWeapons = true;
      },
      error => {
        console.log("Error during loading most effective weapons.\n" + error);
      }
    );
    return null;
  }
}

