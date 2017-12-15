import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Monster } from '../../entity.module';
import {ActivatedRoute, Router} from '@angular/router';
import {CookieService} from "ngx-cookie-service";


@Component({
  selector: 'app-monster-detail',
  templateUrl: './monster-detail.component.html',
  styleUrls: ['./monster-detail.component.scss']
})
export class MonsterDetailComponent implements OnInit {

  monsterId: number;
  showMonster: boolean = false;
  monster: Monster;
  selectedAgility: string;
  cookie: boolean = false;


  constructor(private http: HttpClient, private route: ActivatedRoute, private cookieService: CookieService, private router: Router) {
    this.route.params.subscribe(res => this.monsterId = res.id);
  }

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.loadData();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
      this.router.navigate(['/login']);
    }
  }

  loadData(){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Monster>('http://localhost:8080/pa165/rest/auth/monsters/' + this.monsterId, {withCredentials: true}).subscribe(
    data => {
      console.log('Monster detail loaded:\n' + data);
      this.monster = data;
      this.showMonster = true;
      this.selectedAgility = data.agility == null ? 'null' : data.agility;
      });
  }

  updateMonster(name, height, weight, agility){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    var json = {"name":name, "height":height, "weight":weight, "agility":agility};
    console.log(json);
    this.http.put('http://localhost:8080/pa165/rest/auth/monsters/' + this.monsterId, json, {withCredentials: true}).subscribe(
      data => {
        console.log("Updating monster with name: " + name + ", height: " + height + ", weight: "+ weight + "and agility: " + agility + "was successful.");
        this.loadData();
    }, error => {
      console.log("Error: " + error);
        console.log("Error during updating monster with name: " + name + ", height: " + height + ", weight: "+ weight + "and agility: " + agility + "was successful.");
    }
    )
  }
}
