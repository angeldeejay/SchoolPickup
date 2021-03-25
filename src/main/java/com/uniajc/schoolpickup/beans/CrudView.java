package com.uniajc.schoolpickup.beans;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.services.PickupRequestService;
import com.uniajc.schoolpickup.services.StudentService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class CrudView implements Serializable {
  private List<Student> students;
  private Student selectedStudent;
  private Integer slot;
  private Parent parent;
  private PickupRequest currentPickupRequest;

  @Autowired private StudentService studentService;
  @Autowired private PickupRequestService pickupRequestService;

  @PostConstruct
  public void init() {
    FaceletContext faceletContext =
        (FaceletContext)
            FacesContext.getCurrentInstance()
                .getAttributes()
                .get(FaceletContext.FACELET_CONTEXT_KEY);
    this.parent = (Parent) faceletContext.getAttribute("parent");
    this.selectedStudent = null;
    this.currentPickupRequest = new PickupRequest();
    List<Student> studentsFound = studentService.findByParent(parent);
    this.students = new ArrayList<>() {};
    for (Student s : studentsFound) {
      List<PickupRequest> pending = pickupRequestService.findPending(s);
      if (!pending.isEmpty()) {
        s.setPendingPickupRequest(pending.get(0));
      }
      students.add(s);
    }
  }

  public void loadData() {
    FaceletContext faceletContext =
        (FaceletContext)
            FacesContext.getCurrentInstance()
                .getAttributes()
                .get(FaceletContext.FACELET_CONTEXT_KEY);
    this.parent = (Parent) faceletContext.getAttribute("parent");
    this.selectedStudent = null;
    this.currentPickupRequest = new PickupRequest();
    List<Student> studentsFound = studentService.findByParent(parent);
    this.students = new ArrayList<>() {};
    for (Student s : studentsFound) {
      List<PickupRequest> pending = pickupRequestService.findPending(s);
      if (!pending.isEmpty()) {
        s.setPendingPickupRequest(pending.get(0));
      }
      students.add(s);
    }
  }

  public List<Student> getStudents() {
    return students;
  }

  public Student getSelectedStudent() {
    return selectedStudent;
  }

  public void setSelectedStudent(Student selectedStudent) {
    selectedStudent.setParent(parent);
    this.selectedStudent = selectedStudent;
  }

  public Integer getSlot() {
    return slot;
  }

  public void setSlot(Integer slot) {
    this.slot = slot;
  }

  public PickupRequest getCurrentPickupRequest() {
    return currentPickupRequest;
  }

  public void setCurrentPickupRequest(PickupRequest currentPickupRequest) {
    this.currentPickupRequest = currentPickupRequest;
  }

  public void openNew() {
    this.selectedStudent = new Student();
    PrimeFaces.current().executeScript("PF('dtStudents').unselectAllRows()");
  }

  public void pickupStudent() {
    String opSuffix = this.currentPickupRequest.getId() == null ? "agrega" : "actualiza";
    currentPickupRequest.setParent(parent);
    currentPickupRequest.setStudent(selectedStudent);

    PickupRequest savedPickupRequest = pickupRequestService.saveEntity(currentPickupRequest);

    if (savedPickupRequest.getId() != null) {
      this.students.remove(selectedStudent);
      selectedStudent.setPendingPickupRequest(savedPickupRequest);
      this.students.add(selectedStudent);

      this.selectedStudent = new Student();
      this.currentPickupRequest = new PickupRequest();

      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Solicitud exitosa"));
      PrimeFaces.current().executeScript("PF('pickupStudentDialog').hide()");
      PrimeFaces.current().executeScript("PF('dtStudents').unselectAllRows()");
    } else {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Error al solicitar recogida"));
    }

    PrimeFaces.current().ajax().update("form:dt-students");
  }

  public void pickedUpStudent() {
    this.currentPickupRequest = selectedStudent.getPendingPickupRequest();
    currentPickupRequest.setPickedUpAt(new Date());

    PickupRequest savedPickupRequest = pickupRequestService.saveEntity(currentPickupRequest);

    if (savedPickupRequest.getId() != null) {
      this.students.remove(selectedStudent);
      selectedStudent.setPendingPickupRequest(null);
      this.students.add(selectedStudent);

      this.selectedStudent = new Student();
      this.currentPickupRequest = new PickupRequest();

      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Solicitud exitosa"));
      PrimeFaces.current().executeScript("PF('pickupStudentDialog').hide()");
      PrimeFaces.current().executeScript("PF('dtStudents').unselectAllRows()");
    } else {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Error al solicitar recogida"));
    }

    PrimeFaces.current().ajax().update("form:dt-students");
  }

  public void saveStudent() {
    String opSuffix = this.selectedStudent.getId() == null ? "agrega" : "actualiza";
    selectedStudent.setParent(parent);

    Student savedStudent = studentService.saveEntity(selectedStudent);

    if (savedStudent.getId() != null) {
      savedStudent.setPendingPickupRequest(selectedStudent.getPendingPickupRequest());
      this.students.remove(selectedStudent);
      this.selectedStudent = savedStudent;
      this.students.add(selectedStudent);
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Estudiante " + opSuffix + "do"));
      this.selectedStudent = new Student();
      PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");
      PrimeFaces.current().executeScript("PF('dtStudents').unselectAllRows()");
    } else {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Error al " + opSuffix + "r el estudiante"));
    }

    PrimeFaces.current().ajax().update("form:dt-students");
  }

  public void deleteStudent() {
	pickupRequestService.deleteAllAssociations(selectedStudent);
    studentService.deleteEntity(selectedStudent.getId());
    this.students.remove(selectedStudent);
    this.selectedStudent = null;
	this.currentPickupRequest = new PickupRequest();
    // Llamar para eliminar uno
    this.students.remove(this.selectedStudent);
    this.selectedStudent = null;
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estudiante eliminado"));
    PrimeFaces.current().ajax().update("form:dt-students");
  }
}