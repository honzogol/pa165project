import {Component, Inject, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Monster, Area} from '../../entity.module';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog, MatTableDataSource} from "@angular/material";
import {CookieService} from "ngx-cookie-service";
import {AddMonstersToAreaComponent} from "../../add-monsters-to-area-dialog/add-monsters-to-area-dialog.component";
import {ApplicationConfig, CONFIG_TOKEN} from "../../app-config";

@Component({
  selector: 'app-area-detail',
  templateUrl: './area-detail.component.html',
  styleUrls: ['./area-detail.component.scss']
})
export class AreaDetailComponent implements OnInit {

  displayedColumns = ['id', 'name', 'agility', 'weight', 'height', 'remove'];
  areaId: number;
  showArea: boolean = false;
  area: Area;
  areaType: string;
  monsters: Monster[] = [];
  dataSource: MatTableDataSource<Monster>;

  isAdmin: boolean = false;

  cookie: boolean = false;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private cookieService: CookieService,
              private router: Router,
              private dialog: MatDialog,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) {
    this.route.params.subscribe(res => this.areaId = res.id);

  }

  ngOnInit() {
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIsAdminCookie();
    this.loadData();
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
    this.isAdmin = false;
  }

  loadData(){
    this.showArea = false;
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.get<Area>(this.config.apiEndpoint + '/pa165/rest/auth/areas/' + this.areaId, {withCredentials: true}).subscribe(
      data => {
        console.log('Area detail loaded:\n' + data);
        this.area = data;
        this.showArea = true;
        this.areaType = data.type == null ? 'null' : data.type;
        this.monsters = data.monsters;
        this.dataSource = new MatTableDataSource(this.monsters);
        this.showArea = true;
      });
  }

  showMonsters(){

    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();

    let dialogRef = this.dialog.open(AddMonstersToAreaComponent, {
      width: '600px',
      data: this.area,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("Dialog closed");
      this.loadData();
    });
  }

  removeMonsterFromArea(monsterId){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    this.http.put(this.config.apiEndpoint + '/pa165/rest/auth/areas/' + this.areaId + '/removeMonsterFromArea?monsterId='+ monsterId ,  null, {responseType: 'text', withCredentials: true}).subscribe(
      data => {
        console.log("Removing monster with id: " + monsterId + " from area with id: " + this.areaId + "was successful.");
        this.loadData();
      }, error => {
        console.log("Error during removing monster with id: " + monsterId + " from area with id: " + this.areaId + "was successful.");
      }
)
}

updateArea(name, areaType){
    this.cookie = this.cookieService.check('creatures-token');
    this.checkIfCookieExist();
    var json = {"id":this.areaId,"name": name,"type":areaType};
    this.http.put(this.config.apiEndpoint + '/pa165/rest/auth/areas/update/' + this.areaId, json, {withCredentials: true}).subscribe(
      data => {
        console.log("Updating area with name: " + name + ", type: " + areaType + "was successful.");
        this.loadData();
      }, error => {
        console.log("Error during updating area with name: " + name + ", type: " + areaType + "was successful.");
      }
)
}
}
