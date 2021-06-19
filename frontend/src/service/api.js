import Axios from "axios";

const api = Axios.create({
  baseURL: "http://localhost:8080/api/v1/",
  headers: { Accept: "application/json" },
});

const get = async (url, callback) => {
  const response = await api.get(url);
  callback(response.data);
};

export const login = async (username, password, callback) => {
  const body = { username: username, password: password };
  const response = await api.post("/auth", body);
  callback(response.data.access_token);
};

export const getUser = async (id, callback) => {
  get(`user/${id}`, callback);
};

export const getFieldOfInterests = async (callback) => {
  get("/field", callback);
};

export const getFieldOfInterestUsers = async (id, callback) => {
  get(`/field/${id}/users`, callback);
};
