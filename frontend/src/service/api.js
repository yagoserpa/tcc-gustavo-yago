import Axios from "axios";

const api = Axios.create({
  baseURL: "http://localhost:8080/api/v1/",
  headers: { Accept: "application/json" },
});

export const login = async (username, password, callback) => {
  const body = { username: username, password: password };
  const response = await api.post("/auth", body);
  callback(response.data);
};

export const getUser = async (id, callback) => {
  const response = await api.get(`user/${id}`);
  callback(response.data);
};

export const getFieldOfInterests = async (callback) => {
  const response = await api.get("/field");
  callback(response.data);
};

export const getFieldOfInterestUsers = async (id, callback) => {
  const response = await api.get(`/field/${id}/users`);
  callback(response.data);
};
