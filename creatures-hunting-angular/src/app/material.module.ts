import { NgModule } from '@angular/core';
import { MatButtonModule, MatToolbarModule, MatCardModule, MatGridListModule,
  MatTableModule, MatFormFieldModule, MatProgressSpinnerModule, MatInputModule, MatOptionModule, MatSelectModule, MatStepperModule, MatDialogModule } from '@angular/material';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

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
    MatStepperModule,
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
    FormsModule,
    ReactiveFormsModule,
  ],
})

export class MaterialModule {}
