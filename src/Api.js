const localhost = "http://localhost:8080";
const token = localStorage.getItem("token");

export const getRequestFetchingLogic = async (url) => {
  const response = await fetch(localhost + url, {
    method: "GET",
  });

  console.log("response", response);

  if (!response.ok) {
    throw new Error(`HTTP error: Status ${response.status}`);
  }

  return response.json();
};

export const fetchRequestLogin = async (url, options = {}) => {
  const response = await fetch(localhost + url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(options),
  });

  console.log("response", response);

  if (!response.ok) {
    throw new Error(`HTTP error: Status ${response.status}`);
  }

  return response.json();
};

export const fetchRequest = async (url, options = {}) => {
  const response = await fetch(localhost + url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(options),
  });

  console.log("response", response);

  if (!response.ok) {
    throw new Error(`HTTP error: Status ${response.status}`);
  }
  //error kod edita s ovim
  console.log(response.json());
  return response.json();
};

export const fetchResponseRequest = async (url, options = {}) => {
  const response = await fetch(localhost + url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(options),
  });

  console.log("response", response.json());

  if (!response.ok) {
    throw new Error(`HTTP error: Status ${response.status}`);
  }

  return response;
};
