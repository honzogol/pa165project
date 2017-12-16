import { NgModule } from '@angular/core';
import {
  MatButtonModule, MatToolbarModule, MatCardModule, MatGridListModule,
  MatTableModule, MatFormFieldModule, MatProgressSpinnerModule, MatInputModule, MatOptionModule, MatSelectModule,
  MatStepperModule, MatDialogModule, MatIconModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    MatIconModule,
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
    MatStepperModule,
  ],

  exports: [
    MatIconModule,
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
    FormsModule,
    ReactiveFormsModule,
  ],
})

export class MaterialModule {}
