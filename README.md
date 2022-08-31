# Task List Application

- ## Overview:
  - The app is a simple to-do application where the user can input a task and a dynamic list of tasks are displayed on the screen. 
  - Once saved, the user can check a box to the left of each task or delete the task by swiping left. 
  
- ## Implementation:
  - The UI utilizes a RecyclerViewer to display a dynamic list of tasks when they are added. 
  - Each task utilizes a CardView to have a clean organized look. 
  - To input tasks, a custom dialog fragment is used to take the user’s input and then save it to a data class. 
  - Data is handled through an SQLite database.
  
- ## Instructions: 
  - Add a task – Click floating action button in the bottom right of the phone. A dialog window will pop up. 
  - Input text where it says, “enter task”. To save task, click add. 
  - If you do not wish to save the task, click dismiss. 
  - If no text is entered and the add button is clicked, a Toast window will popup asking you to enter a task. 
  - Check Task Box – check boxes on the task’s CardViews can be checked/unchecked
  - Delete task – swipe left on the task CardView

