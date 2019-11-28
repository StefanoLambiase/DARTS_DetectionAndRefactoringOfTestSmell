package main.action;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.utility.ConverterUtilities;
import main.testSmellDetection.IDetector;
import main.testSmellDetection.detector.TextualDetector;
import main.toolWindowConstruction.TestSmellWindowFactory;
import main.utility.TestSmellUtilities;
import org.apache.xerces.util.SynchronizedSymbolTable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class TextualDetectionAction extends AnAction {

    /* TOOL WINDOW
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //Mi prendo la folder del progetto attivo
        String pFolderPath = anActionEvent.getProject().getBasePath();
        IDetector detector = new TextualDetector(pFolderPath);

        //Eseguo l'analisi
        if(pFolderPath != null){
            ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture();
            ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest();
            ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion();

            //Creo la ToolWindow
            if(listGFI.isEmpty() && listETI.isEmpty()){
                System.out.println("\nNon si è trovato alcuno Smell");
            } else {
                new TestSmellToolWindowFactory().registerToolWindow(true, false, anActionEvent.getProject(), listGFI, listETI, listLOCI);
            }
        } else {
            System.out.println("\nVi è stato un errore con l'ottenumento della folder del progetto attivo");
        }
    }
     */


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //Mi prendo la folder del progetto attivo
        String pFolderPath = anActionEvent.getProject().getBasePath();
        IDetector detector = new TextualDetector(pFolderPath);

        /* ANALISI NUOVA. COMMENTARE PRIMA DI USARE L'ALTRA. */
        ArrayList<PsiClass> classes = usePSI(anActionEvent.getProject());

        /* ANALISI VECCHIA, DECOMMENTARE SE SI VUOLE USARE. */
        /*
        //Eseguo l'analisi
        if(pFolderPath != null){
            ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture();
            ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest();
            ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion();

            //Creo la window
            if(listGFI.isEmpty() && listETI.isEmpty()){
                System.out.println("\nNon si è trovato alcuno Smell");
            } else {
                TestSmellWindowFactory.createWindow(true, false, anActionEvent.getProject(), listGFI, listETI, listLOCI);
            }
        } else {
            System.out.println("\nVi è stato un errore con l'ottenumento della folder del progetto attivo");
        }

         */
    }

    public ArrayList<PsiClass> usePSI(Project myProject){
        ArrayList<PsiClass> classes = ConverterUtilities.getClassesFromPackages(myProject);

        System.out.println("\nSONO IL DETECTOR TESTUALE: ECCO LA LISTA DELLE CLASSI DI TEST:");
        ArrayList<PsiClass> testClasses = TestSmellUtilities.getAllTestClasses(classes);
        if(testClasses.size() > 0){
            for(PsiClass psiClass : testClasses){
                System.out.println("\n" + psiClass.getName());
            }
        }
        System.out.println("\nSONO IL DETECTOR TESTUALE: ECCO LA LISTA DELLE PRODUCTION CLASSES:");
        ArrayList<PsiClass> productionClasses = TestSmellUtilities.getAllProductionClasses(classes, testClasses);
        if(productionClasses.size() > 0){
            for(PsiClass psiClass : productionClasses){
                System.out.println("\n" + psiClass.getName());
            }
        }

        return classes;
    }

}
