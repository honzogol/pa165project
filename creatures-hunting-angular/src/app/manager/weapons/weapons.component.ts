import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { Weapon } from '../../entity.module';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-weapons',
  templateUrl: './weapons.component.html',
  styleUrls: ['./weapons.component.scss']
})
export class WeaponsComponent implements OnInit {

  displayedColumns = ['id', 'name', 'type', 'range', 'magazineCapacity', 'edit', 'remove'];
  showWeapons : boolean = false;
  weapons : Weapon[] = [];
  dataSource : MatTableDataSource<Weapon>;
  cookie: boolean = false;


  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {}

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.loadWeapons();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
      this.router.navigate(['/login']);
    }
  }

  loadWeapons(){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Weapon[]>('http://localhost:8080/pa165/rest/auth/weapons/', {withCredentials: true}).subscribe(
      data => {
        this.weapons = data;
        this.dataSource = new MatTableDataSource(this.weapons);
        console.log('Weapons loaded:\n' + data);
        this.showWeapons = true;
      },
      error => {
        console.log("Error during loading weapons.\n" + error);
      }
    );
  }

  removeWeapon(id){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.delete('http://localhost:8080/pa165/rest/auth/weapons/delete/' + id ,  {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        this.loadWeapons();
        console.log("Removing weapon with id: " + id +"was successful.");
      },
      error => {
        console.log("Error during removing weapon with id: " + id +".");
      }
    );
  }
}
