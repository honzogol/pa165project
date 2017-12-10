import { NgModule } from '@angular/core';
import { MatButtonModule, MatToolbarModule, MatCardModule, MatGridListModule,
  MatTableModule, MatFormFieldModule, MatProgressSpinnerModule, MatInputModule, MatOptionModule, MatSelectModule } from '@angular/material';

@NgModule({
  imports: [MatButtonModule,
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

  exports: [MatButtonModule,
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
