export default class LoggedInUser {
  constructor() {
    this.user = localStorage.getItem("user");
    this._subscribed = [];
  }

  subscribe(func) {
    this._subscribed.push(func);
    func(this.user);
  }

  notify() {
    this._subscribed.forEach((func) => func(this.user));
  }

  unsubscribe(func) {
    this._subscribed = this._subscribed.filter((f) => f !== func);
  }

  saveLoggedInUser(token) {
    this.user = new User(token);
    localStorage.setItem("user", this.user);
    this.notify();
  }

  clearLoggedInUser() {
    localStorage.clear();
    this.user = null;
    this.notify();
  }
}

class User {
  constructor(token) {
    this.token = token;
  }
}
