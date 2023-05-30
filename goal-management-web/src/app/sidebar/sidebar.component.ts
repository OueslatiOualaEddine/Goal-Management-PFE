import { Component, OnInit } from "@angular/core";
import { UtilsService } from "app/service/utils.service";

export interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
  hidden: boolean;
}

export const ROUTES: RouteInfo[] = [
  {
    path: "/dashboard",
    title: "Dashboard",
    icon: "fa fa-home",
    class: "",
    hidden: false,
  },
  {
    path: "/users-management",
    title: "Fiches Utilisateurs",
    icon: "fa fa-address-card",
    class: "",
    hidden: !UtilsService.isAdministrator(),
  },
  {
    path: "/teams-management",
    title: "Équipes",
    icon: "fa fa-group",
    class: "",
    hidden: !UtilsService.isAdministrator(),
  },
  {
    path: "/enterprise-okr",
    title: "OKR d’Entreprise",
    icon: "fa fa-object-group",
    class: "",
    hidden: !UtilsService.isAdministrator(),
  },
  {
    path: "/team-okr",
    title:"OKR d’Équipe",
    icon: "fa fa-list-alt",
    class: "",
    hidden: !UtilsService.isManager(),
  },
  {
    path: "/team-okr",
    title: "Mes Tâches",
    icon: "fa fa-list-alt",
    class: "",
    hidden: !UtilsService.isCollaborator(),
  },
  /*{
    path: "/icons",
    title: "Icons",
    icon: "fa fa-diamond",
    class: "",
    hidden: false,
  },
  {
    path: "/maps",
    title: "Maps",
    icon: "fa fa-map-marker",
    class: "",
    hidden: false,
  },
  {
    path: "/notifications",
    title: "Notifications",
    icon: "fa fa-bell-o",
    class: "",
    hidden: false,
  },
  {
    path: "/user",
    title: "User Profile",
    icon: "fa fa-user",
    class: "",
    hidden: false,
  },
  {
    path: "/table",
    title: "Table List",
    icon: "fa fa-table",
    class: "",
    hidden: false,
  },
  {
    path: "/typography",
    title: "Typography",
    icon: "nc-caps-small",
    class: "",
    hidden: false,
  },
   {
    path: "/upgrade",
    title: "Upgrade to PRO",
    icon: "nc-spaceship",
    class: "active-pro",
  }, */
];

@Component({
  moduleId: module.id,
  selector: "sidebar-cmp",
  templateUrl: "sidebar.component.html",
})
export class SidebarComponent implements OnInit {
  public menuItems: any[];

  ngOnInit() {
    this.menuItems = ROUTES.filter((menuItem) => menuItem.hidden === false);
  }
}
