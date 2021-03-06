package org.zalando.intellij.swagger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Test;
import org.zalando.intellij.swagger.index.IndexFacade;

public class SwaggerJsonCatalogExclusionTest {

  private IndexFacade fakeIndexFacade = mock(IndexFacade.class);
  private ProjectManager fakeProjectManager = mock(ProjectManager.class);

  private final SwaggerJsonCatalogExclusion exclusion =
      new SwaggerJsonCatalogExclusion(fakeIndexFacade, fakeProjectManager);

  @Test
  public void thatIndexIsNotUsedIfItIsNotReady() {
    final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
    final Project fakeProject = mock(Project.class);

    when(fakeProjectManager.getOpenProjects()).thenReturn(new Project[] {fakeProject});
    when(fakeIndexFacade.isIndexReady(fakeProject)).thenReturn(false);

    exclusion.isExcluded(fakeVirtualFile);

    verify(fakeIndexFacade, never()).isMainSpecFile(any(), any());
  }

  @Test
  public void thatFileIsNotExcludedIfIndexIsNotReady() {
    final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
    final Project fakeProject = mock(Project.class);

    when(fakeProjectManager.getOpenProjects()).thenReturn(new Project[] {fakeProject});
    when(fakeIndexFacade.isIndexReady(fakeProject)).thenReturn(false);

    final boolean result = exclusion.isExcluded(fakeVirtualFile);

    assertFalse(result);
  }

  @Test
  public void thatFileIsNotExcludedIfThereAreNoOpenProjects() {
    final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
    final Project fakeProject = mock(Project.class);

    when(fakeProjectManager.getOpenProjects()).thenReturn(new Project[0]);
    when(fakeIndexFacade.isIndexReady(fakeProject)).thenReturn(true);

    final boolean result = exclusion.isExcluded(fakeVirtualFile);

    assertFalse(result);
  }

  @Test
  public void thatSpecFileIsExcluded() {
    final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
    final Project fakeProject = mock(Project.class);

    when(fakeProjectManager.getOpenProjects()).thenReturn(new Project[] {fakeProject});
    when(fakeIndexFacade.isIndexReady(fakeProject)).thenReturn(true);
    when(fakeIndexFacade.isMainSpecFile(fakeVirtualFile, fakeProject)).thenReturn(true);

    final boolean result = exclusion.isExcluded(fakeVirtualFile);

    assertTrue(result);
  }
}
