; UserVars.nsi
;
; This script shows you how to declare and user variables.

;--------------------------------

  Name "BNJ 3.1"
  Caption "Bayesian Network tools in Java"
  OutFile "bnj31_setup.exe"

  CRCCheck on  
  InstallDir "$PROGRAMFILES\BNJ\BNJ3"
  XPStyle on
  SetDatablockOptimize on
  LicenseText "The GNU General Public License (GPL)"
  LicenseData "..\licenses\LICENSE.txt"
;--------------------------------

  Var "Info"


;--------------------------------

  ;Pages
  Page license
  Page directory
;  Page components
  Page instfiles
  
  UninstPage uninstConfirm
  UninstPage instfiles

;--------------------------------
; Installer

Section "Core (required)"

     SectionIn RO
     CreateDirectory $INSTDIR
     SetOutPath $INSTDIR
     File "..\swt-awt-win32-3062.dll"
     File "..\swt-win32-3062.dll"
     File "..\bnjv3.jar"
     File "..\swt.jar"
     File "..\javaw.exe.manifest"
     File "..\run_bnj.bat"
     File "..\*.ini"
     WriteUninstaller "$INSTDIR\Uninst.exe"

     CreateDirectory "$INSTDIR\plugins"
     SetOutPath "$INSTDIR\plugins"
     File "..\plugins\*.jar"

     CreateDirectory "$INSTDIR\imgs"
     SetOutPath "$INSTDIR\imgs"
     File "..\imgs\*.gif"

     CreateDirectory "$SMPROGRAMS\BNJ3"

     SetOutPath $INSTDIR
     CreateShortCut "$SMPROGRAMS\BNJ3\Bayesian Network tool in Java.lnk" "%windir%\System32\javaw" "-ea -esa -Xmx256M -Xms256M -classpath $\".\bnjv3.jar;.\swt.jar;%CLASSPATH%$\" edu.ksu.cis.bnj.ver3.drivers.MASTER"
     CreateShortCut "$SMPROGRAMS\BNJ3\Uninstall.lnk" "$INSTDIR\Uninst.exe" "" "$INSTDIR\Uninst.exe" 0

SectionEnd

Section "Example Networks"
     SectionIn RO
     SetOutPath $INSTDIR
     File "..\*.xml"

SectionEnd

;--------------------------------
; Uninstaller

Section "Uninstall"

     Delete "$INSTDIR\swt-awt-win32-3062.dll"
     Delete "$INSTDIR\swt-win32-3062.dll"
     Delete "$INSTDIR\bnjv3.jar"
     Delete "$INSTDIR\swt.jar"
     Delete "$INSTDIR\javaw.exe.manifest"
     Delete "$INSTDIR\run_bnj.bat"
     Delete "$INSTDIR\*.ini"
     Delete "$INSTDIR\*.xml"
     Delete "$INSTDIR\*.ini"
     Delete "$INSTDIR\imgs\*.gif"
     Delete "$INSTDIR\plugins\*.jar"

     StrCpy $Info "BNJ 3.0 uninstalled successfully."
     Delete "$INSTDIR\Uninst.exe"
     RmDir "$INSTDIR\imgs"
     RmDir "$INSTDIR\plugins\"
     RmDir $INSTDIR

SectionEnd

Function un.OnUninstSuccess

     HideWindow
     MessageBox MB_OK "$Info"
     
FunctionEnd
