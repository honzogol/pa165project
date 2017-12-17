import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import {ManagerComponent} from "./manager/manager.component";
import {HomeComponent} from "./manager/home/home.component";
import {MonstersComponent} from "./manager/monsters/monsters.component";
import {WeaponsComponent} from "./manager/weapons/weapons.component";
import {MonsterDetailComponent} from "./manager/monster-detail/monster-detail.component";
import {MonsterCreateComponent} from "./manager/monster-create/monster-create.component";
import {WeaponCreateComponent} from "./manager/weapon-create/weapon-create.component";
import {WeaponDetailComponent} from "./manager/weapon-detail/weapon-detail.component";
import {UsersComponent} from "./manager/users/users.component";
import {Error404Component} from "./error404/error404.component";
import {UserCreateComponent} from "./manager/user-create/user-create.component";
import {AreasComponent} from "./manager/areas/areas.component";
import {AreaDetailComponent} from "./manager/area-detail/area-detail.component";
import {AreaCreateComponent} from "./manager/area-create/area-create.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: ManagerComponent,
    children: [
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'monsters',
        component: MonstersComponent
      },
      {
        path: 'monsters/:id',
        component: MonsterDetailComponent
      },
      {
        path: 'create/monster',
        component: MonsterCreateComponent
      },
      {
        path: 'weapons',
        component: WeaponsComponent
      },
      {
        path: 'weapons/:id',
        component: WeaponDetailComponent
      },
      {
        path: 'create/weapon',
        component: WeaponCreateComponent
      },
      {
        path: 'users',
        component: UsersComponent
      },
      {
        path: 'create/user',
        component: UserCreateComponent
      },
      {
        path: 'areas',
        component: AreasComponent
      },
      {
        path: 'areas/:id',
        component: AreaDetailComponent
      },
      {
        path: 'areas/new',
        component: AreaCreateComponent
      }
    ]
  },
  {
    path:'**',
    component: Error404Component
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
