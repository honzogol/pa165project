import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material";
import { HttpClient } from '@angular/common/http';
import {User} from "../../entity.module";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
	selector: 'app-users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  displayedColumns = ['id', 'firstName', 'lastName', 'email', 'role', 'delete', 'switchRole' ];
  showUsers: boolean = false;
  users: User[] = [];
  dataSource: MatTableDataSource<User>;
  cookie: boolean = false;

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) { }

	ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
	  this.loadUsers();
	}

  checkIfCookieExist(){
    if (!this.cookie){
      alert("You must log in.");
      this.router.navigate(['/login']);
    }
  }

  loadUsers() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<User[]>('http://localhost:8080/pa165/rest/auth/users/', { withCredentials: true }).subscribe(
      data => {
        this.users = data;
        this.dataSource = new MatTableDataSource(this.users);
        console.log('Users loaded:\n' + data);
        this.showUsers = true;
      },
      error => {
        console.log("Error during loading monsters.\n" + error);
      });
  }

  deleteUser(id) {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.delete('http://localhost:8080/pa165/rest/auth/users/' + id,  {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Deleting user with id: " + id +"was successful.");
        this.loadUsers();
      },
      error => {
        console.log("Error during deleting user with id: " + id +".");
      }
    );
  }

  setAdmin(id) {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.put('http://localhost:8080/pa165/rest/auth/users/setAdmin?id=' + id,  null,{responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Setting user with id: " + id +" as admin was successful.");
        this.loadUsers();
      },
    error => {
      console.log("Error during setting user with id: " + id +"as admin.");
      }
    );
  }

  removeAdmin(id) {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.put('http://localhost:8080/pa165/rest/auth/users/removeAdmin?id=' + id,  null,{responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Setting user with id: " + id +" as regular user was successful.");
        this.loadUsers();
      },
      error => {
        console.log("Error during setting user with id: " + id +"as regular user.");
      }
    );
  }

  isAdmin(id) {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get('http://localhost:8080/pa165/rest/auth/users/isAdmin?id=' + id,  {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        this.loadUsers();
      }
    );
  }

  switchRole(id, role) {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    if(role == "ADMIN") {
      this.removeAdmin(id);
    } else {
      this.setAdmin(id);
    }
  }




}
