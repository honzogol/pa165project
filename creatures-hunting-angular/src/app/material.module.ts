import { NgModule } from '@angular/core';
import {
  MatButtonModule, MatToolbarModule, MatCardModule, MatGridListModule,
  MatTableModule, MatFormFieldModule, MatProgressSpinnerModule, MatInputModule, MatOptionModule, MatSelectModule,
  MatDialogModule
} from '@angular/material';

@NgModule({
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatGridListModule,
    MatTableModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
  ],

  exports: [
    MatDialogModule,
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatGridListModule,
    MatTableModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
  ],
})

export class MaterialModule {}
