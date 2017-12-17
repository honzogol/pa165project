import {Component, Inject, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Monster } from '../../entity.module';
import {ActivatedRoute, Router} from '@angular/router';
import {CookieService} from "ngx-cookie-service";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";
import {ErrorDialogComponent} from "../../error-dialog/error-dialog.component";
import {MatDialog} from "@angular/material";


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
  isAdmin: boolean = false;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private cookieService: CookieService,
              private router: Router,
              private dialog: MatDialog,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {
    this.route.params.subscribe(res => this.monsterId = res.id);
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

  checkIsAdminCookie(){
    if (this.cookieService.get('creatures-is_admin') == "true"){
      this.isAdmin = true;
      return;
    }
    this.isAdmin = false;
  }

  checkIfCookieExist(){
    if (!this.cookie){
      this.router.navigate(['/login']);
    }
  }

  loadData(){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Monster>(this.config.apiEndpoint + '/pa165/rest/auth/monsters/' + this.monsterId, {withCredentials: true}).subscribe(
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
    this.http.put(this.config.apiEndpoint + '/pa165/rest/auth/monsters/' + this.monsterId, json, {withCredentials: true}).subscribe(
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
