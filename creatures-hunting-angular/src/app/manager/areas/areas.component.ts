import {Component, Inject, OnInit} from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { Area } from '../../entity.module';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";

@Component({
  selector: 'app-areas',
  templateUrl: './areas.component.html',
  styleUrls: ['./areas.component.scss']
})
export class AreasComponent implements OnInit {

  displayedColumns = ['id', 'name', 'type', 'detail', 'remove'];
  showAreas : boolean = false;
  areas : Area[] = [];
  dataSource : MatTableDataSource<Area>;
  cookie: boolean = false;
  isAdmin: boolean = false;

  constructor(private http: HttpClient,
              private cookieService: CookieService,
              private router: Router,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {}

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIsAdminCookie();
    this.loadAreas();
  }

  checkIfCookieExist(){
    if (!this.cookie){
      this.router.navigate(['/login']);
    }
  }

  checkIsAdminCookie(){
    if (this.cookieService.get('creatures-is_admin') == "true"){
      this.isAdmin = true;
      return;
    }
    this.displayedColumns = ['id', 'name', 'type', 'detail'];
    this.isAdmin = false;
  }

  loadAreas(){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Area[]>(this.config.apiEndpoint + '/pa165/rest/auth/areas/', {withCredentials: true}).subscribe(
      data => {
        this.areas = data;
        this.dataSource = new MatTableDataSource(this.areas);
        console.log('Areas loaded:\n' + data);
        this.showAreas = true;
      },
      error => {
        console.log("Error during loading areas.\n" + error);
      }
    );
  }

  removeArea(id){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.delete(this.config.apiEndpoint + '/pa165/rest/auth/areas/delete/' + id ,  {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        this.loadAreas();
        console.log("Removing area with id: " + id + " was successful.");
      },
      error => {
        console.log("Error during removing area with id: " + id +".");
      }
    );
  }
}
