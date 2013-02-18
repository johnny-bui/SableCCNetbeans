package org.sableccsupport;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;
// "org-netbeans-modules-java-j2seproject"

public class SableCCCompositePanelProvider implements ProjectCustomizer.CompositeCategoryProvider {
private final String name;

    private SableCCCompositePanelProvider(String name) {
        this.name = name;
    }

    @Override
    public Category createCategory(Lookup lkp) {
        return ProjectCustomizer.Category.create(name, name, null);
    }

    @Override
    public JComponent createComponent(Category category, Lookup lkp) {
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
		//Project prj = Lookup.getDefault().lookup(Project.class);
		//String projectRoot = FileUtil.getFileDisplayName(prj.getProjectDirectory());
        jPanel1.add(new JLabel("xxxxxxxxxxx"), BorderLayout.CENTER);
        return jPanel1;
    }

    //@NbBundle.Messages({"LBL_Config=Configuration"})
    @ProjectCustomizer.CompositeCategoryProvider.Registration(
        projectType = "org-netbeans-modules-java-j2seproject", 
        position = 10)
    public static SableCCCompositePanelProvider createMyDemoConfigurationTab() {
        return new SableCCCompositePanelProvider(/*Bundle.LBL_Config()*/"Config!!!");
    }
	
}