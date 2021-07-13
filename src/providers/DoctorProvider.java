package providers;

import models.DoctorUser;

public final class DoctorProvider {

  private DoctorUser user;
  private final static DoctorProvider INSTANCE = new DoctorProvider();

  private DoctorProvider() {
  }

  public static DoctorProvider getInstance() {
    return INSTANCE;
  }

  public void setUser(DoctorUser u) {
    this.user = u;
  }

  public DoctorUser getUser() {
    return this.user;
  }
}