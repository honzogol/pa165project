import {Component, Inject, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import { HttpClient } from '@angular/common/http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.scss']
})
export class UserCreateComponent implements OnInit {


  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  cookie: boolean = false;

  constructor(private http: HttpClient,
              private cookieService: CookieService,
              private router: Router,
              private _formBuilder: FormBuilder,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {}

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.thirdFormGroup = this._formBuilder.group({
      thirdCtrl: ['', Validators.required]
    });
    this.fourthFormGroup = this._formBuilder.group({
      fourthCtrl: ['', Validators.required],
      fifthCtrl: ['', Validators.required]
    }, {validator: this.areEqual});
  }

  checkIfCookieExist(){
    if (!this.cookie){
      this.router.navigate(['/login']);
    }
  }

  createUser(firstName, lastName, email, password){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    var json = {"firstName": firstName,"lastName": lastName, "email": email};
    console.log(json);
    this.http.post(this.config.apiEndpoint + '/pa165/rest/auth/users/register?unencryptedPassword=' + password, json, {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Creating user with first name: " + firstName + ", last name: " + lastName + ", email: "+ email + "was successful.");
        this.router.navigate(['users']);
      }, error => {
        console.log("Error during creating user with first name: " + firstName + ", last name: " + lastName + ", email: "+ email + ".");
        console.log(error);
      }
    )

  }

  areEqual(group: FormGroup) {
    var val;
    var valid = true;

    for (var control in group.controls) {

      if (val === undefined) {
        val = group.controls[control].value
      } else {
        if (val !== group.controls[control].value) {
          valid = false;
          break;
        }
      }
    }

    if (valid) {
      return null;
    }

    return {
      areEqual: true
    };
  }

}
