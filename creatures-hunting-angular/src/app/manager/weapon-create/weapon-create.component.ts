import {Component, Inject, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {WeaponsComponent} from "../weapons/weapons.component";
import {Router} from "@angular/router";
import {Weapon} from "../../entity.module";
import {CookieService} from "ngx-cookie-service";
@Component({
  selector: 'app-weapon-create',
  templateUrl: './weapon-create.component.html',
  styleUrls: ['./weapon-create.component.scss']
})
export class WeaponCreateComponent implements OnInit {

  cookie: boolean = false;

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {}

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

  createWeapon(name, weaponType, range, magazineCapacity){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    var json = {"name": name,"type":weaponType, "range":range, "magazineCapacity":magazineCapacity};
    console.log(json);
    this.http.post<Weapon>('http://localhost:8080/pa165/rest/auth/weapons/create', json, {withCredentials: true}).subscribe(
      data => {
        console.log("Creating weapon with name: " + name + ", type: " + weaponType + ", range: "+ range + "and magazine capacity: " + magazineCapacity + "was successful.");
        this.router.navigate(['weapons']);
      }, error => {
        console.log("Error during creating weapon with name: " + name + ", type: " + weaponType + ", range: "+ range + "and magazine capacity: " + magazineCapacity + "was successful.");
      }
    )

  }
}
