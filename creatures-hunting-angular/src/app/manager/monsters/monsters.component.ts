import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { Monster } from '../../entity.module';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-monsters',
  templateUrl: './monsters.component.html',
  styleUrls: ['./monsters.component.scss']
})
export class MonstersComponent implements OnInit {

  displayedColumns = ['id', 'name', 'agility', 'height', 'weight', 'edit', 'remove'];
  showMonsters: boolean = false;
  monsters: Monster[] = [];
  dataSource: MatTableDataSource<Monster>;
  cookie: boolean = false;

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {}

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.loadMonsters();
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
    this.http.get<Monster[]>('http://localhost:8080/pa165/rest/auth/monsters/', { withCredentials: true }).subscribe(
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
    this.http.delete('http://localhost:8080/pa165/rest/auth/monsters/' + id,  {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Removing monster with id: " + id +"was successful.");
        this.loadMonsters();
      },
      error => {
        console.log("Error during removing monster with id: " + id +".");
      });
  }
}

