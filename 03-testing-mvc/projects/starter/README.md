# Instructions for Authors and Editors

1. Clone the repository 
2. Switch to the branch for your chapter
3. Commit changes
4. Push changes
5. `GOTO 3`
6. Finally, @-mention the FPE on the open pull request for your chapter letting them know you're done

**Note:** You must only ever work within your branch, and **NEVER** close or merge the corresponding pull request

# Instructions for FPEs

### Setting up a new book

1. Create a repository, making sure to include a blank README and the Swift gitignore file
2. Add the repository to your team
3. Delete the existing labels, and add the raywenderlich.com flavored labels
4. Add the contents of this file to the README and commit
5. Clone the repository
6. Run `make-codex-subdirectory`, once for each chapter passing the chapter name as the branch name
7. Run `git push --all` to push the changes up to GitHub
8. In GitHub, open a pull request for each branch
9. Assign the corresponding author to each pull request, and change the label to **Sample Project**
10. Finally, @-mention the author to let them know they can begin

### Handing off a chapter

1. Update the assignee
2. Update the label
3. Finally, @-mention the new assignee to let them know they can begin

### Completing a chapter

1. Merge the pull request into `master`
