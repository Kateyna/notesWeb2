import { Component, Inject } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';


@Component({
  imports: [
    // Добавляем обязательные импорты

    MatDialogContent,
    MatDialogActions,
    MatDialogClose, // <-- этот импорт критически важен
    MatButtonModule
  ],
  selector: 'app-dialology',
  styleUrl: './dialology.component.css',
  templateUrl: './dialology.component.html'

})
export class DialologyComponent {
  constructor(
    public dialogRef: MatDialogRef<DialologyComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { name: string }
  ) {}
}
