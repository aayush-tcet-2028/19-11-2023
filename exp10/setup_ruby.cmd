@echo off
setlocal DisableDelayedExpansion
title Ruby on Rails - Experiment 10
if exist "%SystemDrive%\Ruby34-x64\bin\ruby.exe" set "PATH=%SystemDrive%\Ruby34-x64\bin;%PATH%"
where ruby >nul 2>&1
if errorlevel 1 (
  echo Install Ruby+Devkit from https://rubyinstaller.org/downloads/ first.
  echo Then open a new terminal and run this file again. See README.md.
  pause
  exit /b 1
)
ruby "%~dp0setup.rb" %*
set "NWT_EXIT=%ERRORLEVEL%"
echo.
pause
exit /b %NWT_EXIT%
