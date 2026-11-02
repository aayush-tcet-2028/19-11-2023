# Experiment 10: Simple Ruby on Rails Application

## Aim

Create a student application using MVC architecture and basic Create, Read, Update, and Delete (CRUD) operations.

## Requirements

Install Ruby+Devkit 3.4 on Windows and complete its development-tool setup. Internet is needed to install missing Rails dependencies. See [README.md](README.md) for detailed setup and GitHub instructions.

## Run Steps

Open a terminal in the main practicals folder and run:

```powershell
cd exp10
.\setup_ruby.cmd
```

Setup automatically creates the application in `exp10/exp10_app`, installs dependencies, generates a student scaffold, prepares SQLite, and starts the server. On Windows, double-clicking `exp10/setup_ruby.cmd` performs the same setup. The launcher finds Ruby at its standard installation path even if the terminal reports that `ruby` is not recognized. Direct `ruby setup.rb` also works once Ruby is available on PATH; restart VS Code after installing Ruby.

Open **http://localhost:3000/students**. Stop the server with **Ctrl+C**.

To start an existing app another day, open a terminal in **exp10** and run:

```powershell
.\setup_ruby.cmd
```

## Commands Run by Setup

| Command | Purpose |
| --- | --- |
| `gem install rails --version 8.1.4 --no-document` | Install Rails if it is missing. |
| `rails new` with minimal options | Generate the application framework. |
| `bundle install` | Install dependencies using the supplied Gemfile and lockfile. |
| `ruby bin/rails generate scaffold Student name:string` | Generate the model, views, controller, and migration. |
| `ruby bin/rails db:prepare` | Create the SQLite database and apply migrations. |
| `ruby bin/rails server -b 127.0.0.1` | Start the local development server. |

## CRUD Demonstration

| Operation | Action |
| --- | --- |
| Create | Click New student, enter a name, and save. |
| Read | View the list or open a student's details. |
| Update | Edit an existing student's name and save. |
| Delete | Open a student and click the destroy button. |

Blank names are rejected. Records are saved in `exp10_app/storage/development.sqlite3` and remain available after restarting.

## MVC

| Part | Generated file | Purpose |
| --- | --- | --- |
| Model | `app/models/student.rb` | Validates names and reads/writes records. |
| Views | `app/views/students/` | Display the list, details, and forms. |
| Controller | `app/controllers/students_controller.rb` | Handles requests and CRUD operations. |
| Routes | `config/routes.rb` | Connects URLs to controller actions. |

## Result

A simple Ruby on Rails application demonstrates MVC and all four CRUD operations using SQLite. Git tracks the small setup source and dependency configuration; local downloads and the generated app are ignored.
