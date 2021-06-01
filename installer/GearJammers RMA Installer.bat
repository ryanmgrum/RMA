@echo off
echo Welcome to GearJammers's RMA Installer.
echo Please make sure you run this installer as close to the root of the drive as possible (such as in "C:\RMA") to avoid potential path length issues when installing SQL Server 2019.
echo In addition, please make sure this Batch script is being run with Administrative privileges (right-click and select "Run as administrator").
cd /d "%~dp0"
pause
echo Installing JDK 11.0.10...
rem jdk-11.0.10_windows-x64_bin.exe /s ADDLOCAL="ToolsFeature,SourceFeature"
jdk-11.0.10_windows-x64_bin.exe /s
echo JDK v11.0.10 successfully installed.
pause
echo Installing SQL Server 2019...
rem SQL2019-SSEI-Expr.exe /ConfigurationFile=installConfig.ini
SQLEXPR_x64_ENU\SETUP.EXE /ConfigurationFile=installConfig.ini
echo.
echo.
echo SQL Server 2019 successfully installed.
pause
echo Creating Windows Firewall rules...
echo Adding Inbound "SQL Server Port"...
netsh advfirewall firewall add rule name="SQL Server Port" dir=in action=allow protocol=TCP localport=1433
echo Adding Inbound "SQL Server Browser Port"...
netsh advfirewall firewall add rule name="SQL Server Browser Port" dir=in action=allow protocol=UDP localport=1434
echo Windows Firewall rules installed!
pause
rem echo Setting system environment variables...
rem setx JAVA_HOME "%PROGRAMFILES%\Java\jdk-11.0.10" /M
rem for /f "usebackq tokens=2,*" %%A in (`reg query "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v PATH`) do set SYSPATH=%%B
rem setx PATH "%SYSPATH%;%JAVA_HOME%\bin" /M
rem echo Setting system environment variables complete.
rem pause
echo Running RMA database scripts...
"%programfiles%\Microsoft SQL Server\Client SDK\ODBC\170\Tools\Binn\SQLCMD.EXE" -S localhost\SQLEXPRESS -i "RMA Database DDL v9.sql"
"%programfiles%\Microsoft SQL Server\Client SDK\ODBC\170\Tools\Binn\SQLCMD.EXE" -S localhost\SQLEXPRESS -d rma -i "RMA Database DML v9.sql"
"%programfiles%\Microsoft SQL Server\Client SDK\ODBC\170\Tools\Binn\SQLCMD.EXE" -S localhost\SQLEXPRESS -i "Create Logins.sql"
echo RMA database creation and setup complete!
echo You may now execute "RMA.jar" by double-clicking on it.
pause