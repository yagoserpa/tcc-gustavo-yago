import React from "react";
import { useEffect } from "react";
import { useContext } from "react";
import { useState } from "react";
import { api } from "../service/api";

export function useAuth() {
  const context = useContext(AuthContext);

  return context;
}

const AuthContext = React.createContext({});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storagedUser = localStorage.getItem("@GeProFi:user");

    if (storagedUser) {
      setUser(storagedUser);
      api.defaults.headers.Authorization = `Bearer ${storagedUser.token}`;
    }
  }, []);

  async function login(username, password, callback) {
    const body = { username: username, password: password };
    const response = await api.post("/auth", body);
    setUser(response.data);
    localStorage.setItem("@GeProFi:user", response.data);
    api.defaults.headers.Authorization = `Bearer ${response.data.token}`;
    callback(response.data);
  }

  function logout(callback) {
    setUser(null);
    localStorage.removeItem("@GeProFi:user");
    callback();
  }

  return (
    <AuthContext.Provider
      value={{ signed: Boolean(user), user: user, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
