import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";

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
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {}

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
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
      }, error => {
        console.log("Error during creating monster with name: " + name + ", height: " + height + ", weight: "+ weight + "and agility: " + agility + ".");
      }
    );
  }
}
