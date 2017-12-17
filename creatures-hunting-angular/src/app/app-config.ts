import {InjectionToken} from "@angular/core";

export interface ApplicationConfig {
  appName: string;
  apiEndpoint: string;
}

// Configuration values for our app
export const CONFIG: ApplicationConfig = {
  appName: 'Creatures hunting',
  apiEndpoint: 'http://localhost:8080'
};

export const CONFIG_TOKEN = new InjectionToken<ApplicationConfig>('config');
