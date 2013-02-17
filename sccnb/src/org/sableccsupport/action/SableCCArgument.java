
package org.sableccsupport.action;

import java.io.File;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbPreferences;
import org.sableccsupport.SableCCPanel;

/**
 *
 * @author phucluoi
 * @version Feb 17, 2013
 */
public class SableCCArgument {
	private FileObject file;
	public void setSableCCFile(FileObject sableCCFile){
		this.file = sableCCFile;
	}
	
	public String getSableCCFileName(){
		String fileDisplayName = FileUtil.getFileDisplayName(file);
		return fileDisplayName;
	}
	
	/**
	 * TODO: get the project's destination directory if any and use it
	 * if it is set instead of using default configurations
	 */
	public String getDestinationDir(){
		String destinationDir = NbPreferences.forModule(SableCCPanel.class)
					.get(SableCCPanel.SCC_OUTPUT_DIRECTORY_KEY, "");
		String parentDir = FileUtil.toFile(file).getParent();
		if (destinationDir == null){
			destinationDir = parentDir;
		}else{
			destinationDir = destinationDir.trim();
			if (destinationDir.length() == 0){
				destinationDir = parentDir;
			}else{
				// combine the relative destinate path with the project's root path
				Project currentProject = FileOwnerQuery.getOwner(file);
				if (currentProject == null){
					destinationDir = parentDir;
				}else{
					FileObject projectRootDir = currentProject.getProjectDirectory();
					File rootPath = FileUtil.toFile(projectRootDir);
					File destPath = new File(rootPath, destinationDir);
					destinationDir = destPath.getAbsolutePath();
				}
			}
		}
		return destinationDir;
	}
}
