import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";
import {AddMonstersComponent} from "../../add-monsters-dialog/add-monsters-dialog.component";
import {MatDialog} from "@angular/material";
import {ErrorDialogComponent} from "../../error-dialog/error-dialog.component";

@Component({
  selector: 'app-monster-create',
  templateUrl: './monster-create.component.html',
  styleUrls: ['./monster-create.component.scss']
})
export class MonsterCreateComponent implements OnInit {

  cookie: boolean = false;

  agility: string;

  constructor(private http: HttpClient,
              private cookieService: CookieService,
              private router: Router,
              private dialog: MatDialog,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {
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
    this.checkIfCookieExist();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      this.router.navigate(['/login']);
    }
  }

  createMonster(name, height, weight, agility){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    var json = {"name":name, "height":height, "weight":weight, "agility":agility};
    this.http.post(this.config.apiEndpoint + '/pa165/rest/auth/monsters/create', json, {withCredentials: true}).subscribe(
      data => {
        console.log("Creating monster with name: " + name + ", height: " + height + ", weight: "+ weight + "and agility: " + agility + "was successful.");
        this.router.navigate(['monsters']);
      }, (error: HttpErrorResponse) => {
        console.log("Error during creating monster with name: " + name + ", height: " + height + ", weight: "+ weight + "and agility: " + agility + ".");
      }
    );
  }
}
