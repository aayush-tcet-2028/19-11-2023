# Generate a Ruby on Rails App Using Terminal Commands

Use this guide when Ruby is already installed. Rails generates the application code and CRUD pages; no manual Ruby, HTML, or configuration editing is required. These steps work independently of this repository's setup scripts.

## Before Starting

- Use Ruby 3.2 or newer. On Windows, Ruby+Devkit 3.4 is recommended because some dependencies need a compiler.
- Ruby and gem must be available in your terminal. Restart VS Code after installing Ruby if necessary.
- Internet is required to install Rails and the application's dependencies.
- Open a terminal in the folder where you want to create your app. `my_app` must be a new folder name.

If Ruby is installed at `C:\Ruby34-x64` but PowerShell cannot find it, run this first:

```powershell
$env:Path = 'C:\Ruby34-x64\bin;' + $env:Path
```

## Six Commands to Create and Run the App

Run each command after the previous one finishes:

```powershell
gem install rails --version 8.1.4 --no-document
rails _8.1.4_ new my_app --minimal --skip-git --skip-docker --skip-kamal --skip-thruster --skip-bootsnap
cd my_app
ruby bin/rails generate scaffold Student name:string
ruby bin/rails db:migrate
ruby bin/rails server -b 127.0.0.1
```

Open **http://localhost:3000/students** in your browser.

| Command | What it does |
| --- | --- |
| `gem install rails` | Installs the Rails framework. |
| `rails new my_app` | Creates the app and installs its dependencies. |
| `cd my_app` | Enters the generated application folder. |
| `generate scaffold Student name:string` | Generates the student model, controller, views, routes, and migration. |
| `db:migrate` | Creates the student table in SQLite. |
| `server` | Starts the local development server. |

## Demonstrate CRUD

- **Create:** Click New student, enter a name, and save.
- **Read:** View the student list or open a student's details.
- **Update:** Edit a student's name and save.
- **Delete:** Open a student and click its destroy button.

Press **Ctrl+C** in the terminal to stop the server. Saved records remain in `storage/development.sqlite3`.

## Run the Same App Again

Open a terminal in `my_app` and run:

```powershell
ruby bin/rails server -b 127.0.0.1
```

If port 3000 is occupied, add `-p 3001` to the server command and open **http://localhost:3001/students**.

## Use Your Own App and Record Names

Replace `my_app` with your chosen app folder name in both the creation and cd commands. Replace `Student name:string` with another singular model and its fields, such as `Book title:string author:string`. For that example, open **http://localhost:3000/books** after migration and server startup.
