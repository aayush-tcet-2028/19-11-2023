# Ruby on Rails Practical - Experiment 10

Generate and run a simple **Student CRUD application** with Ruby on Rails. The setup creates the Rails app, installs dependencies, generates the student scaffold, prepares SQLite, and starts the server.

All Experiment 10 files are in this **exp10** folder: `setup.rb`, `setup_ruby.cmd`, `ruby_setup/`, this README, and the experiment document. The generated app is also kept here in `exp10_app/`.

For a standalone approach using only terminal commands after Ruby is installed, see [Generic Rails App Generator Steps](GENERIC_RAILS_APP_STEPS.md). No setup script or manual application code is needed for that guide.

## Requirements

- Ruby 3.2 or newer; **Ruby+Devkit 3.4 is recommended for Windows**.
- Internet access for the first setup and any missing dependencies.
- Git if you are cloning this repository.

On Windows, install [Ruby+Devkit](https://rubyinstaller.org/downloads/), enable its PATH option, and complete the development-tool setup. If needed, run `ridk install 1 3`. Restart VS Code afterward so its integrated terminals inherit the updated PATH.

## Create and Run the App

After installing Ruby, run these commands from the main practicals folder in Windows PowerShell:

```powershell
cd exp10
.\setup_ruby.cmd
```

If you already downloaded this repository, open a terminal in **exp10** and run **`.\setup_ruby.cmd`**. From the main practicals folder, run `cd exp10` first. You can also double-click **exp10/setup_ruby.cmd**. The launcher detects `C:\Ruby34-x64\bin\ruby.exe` even when the terminal's PATH has not refreshed. On other operating systems, use `ruby setup.rb` with Ruby available on PATH.

If PowerShell says **ruby is not recognized**, use the launcher above. To enable direct Ruby commands in the current PowerShell session, run:

```powershell
$env:Path = 'C:\Ruby34-x64\bin;' + $env:Path
ruby --version
```

Ruby is already installed on this PC at that location; reinstalling it is unnecessary. Restarting VS Code refreshes its inherited PATH for future terminals.

Open **http://localhost:3000/students**. Add, view, edit, and delete student names. Blank names are rejected. Stop the server with **Ctrl+C**.

The app is generated in **exp10_app/**, which Git ignores. SQLite keeps records in `exp10_app/storage/development.sqlite3`. Running setup again preserves the existing app and records.

## Other Run Options

Generate the app without starting the server:

```powershell
.\setup_ruby.cmd --setup-only
```

Choose an empty app folder:

```powershell
.\setup_ruby.cmd "D:\MyPracticals\exp10"
```

Start a previously generated app again from the **exp10** folder. Setup reuses it and preserves records:

```powershell
.\setup_ruby.cmd
```

If port 3000 is busy, add `-p 3001` and open http://localhost:3001/students.

## What Setup Runs Automatically

1. Installs Rails 8.1.4 if missing.
2. Runs `rails new` with a minimal configuration.
3. Installs the dependencies specified in `ruby_setup/Gemfile` and `Gemfile.lock`.
4. Runs `ruby bin/rails generate scaffold Student name:string` and adds name validation.
5. Runs `ruby bin/rails db:prepare`.
6. Starts `ruby bin/rails server -b 127.0.0.1`.

No installers, gems, compiled libraries, or generated application files need to be committed. The setup downloads missing dependencies when you run it.

## Push to a New GitHub Repository

The previous repository metadata has been detached from this workspace. The offline bundle is outside this workspace, and `exp10/.gitignore` excludes the generated app and downloaded packages. You can initialize a fresh repository with new history and a new remote.

Create an empty repository on GitHub. Replace the URL below with your new repository's URL, then run these commands from the **main practicals folder**, one level above exp10. **No initialization, commit, or push was performed automatically.**

```powershell
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/YOUR_NEW_REPOSITORY.git
git push -u origin main
```

Keep `.gitignore` so generated dependencies and local credentials stay out of the new repository. [GitHub's large-file documentation](https://docs.github.com/en/repositories/working-with-files/managing-large-files/about-large-files-on-github)

## Practical Notes

See [exp10_ruby_on_rails.md](exp10_ruby_on_rails.md) for the aim, MVC mapping, and CRUD demonstration.
