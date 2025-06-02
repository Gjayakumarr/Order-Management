import axios from 'axios';

const BASE_URL = 'http://localhost:8989';

const defaultHeaders = {
  'Content-Type': 'application/json',
};

export const apiPOSTRequest = async (endpoint, body = {}, customHeaders = {}) => {
  const headers = { ...defaultHeaders, ...customHeaders };

  try {
    const response = await axios.post(`${BASE_URL}${endpoint}`, body, { headers });
    return response.data;
  } catch (error) {
    console.error(`API POST ${endpoint} error:`, error);
    throw new Error(error.response?.data?.message || 'API request failed');
  }
};

export const apiGetRequest = async (endpoint, customHeaders = {}) => {
  const headers = { ...defaultHeaders, ...customHeaders };

  try {
    const response = await axios.get(`${BASE_URL}${endpoint}`, { headers });
    return response.data;
  } catch (error) {
    console.error(`API GET ${endpoint} error:`, error);
    throw new Error(error.response?.data?.message || 'API request failed');
  }
};

// PUT request function
export const apiPUTRequest = async (endpoint, body = {}, customHeaders = {}) => {
  const headers = { ...defaultHeaders, ...customHeaders };

  try {
    const response = await axios.put(`${BASE_URL}${endpoint}`, body, { headers });
    return response.data;
  } catch (error) {
    console.error(`API PUT ${endpoint} error:`, error);
    throw new Error(error.response?.data?.message || 'API request failed');
  }
};

// DELETE request function
export const apiDELETERequest = async (endpoint, customHeaders = {}) => {
  const headers = { ...defaultHeaders, ...customHeaders };

  try {
    const response = await axios.delete(`${BASE_URL}${endpoint}`, { headers });
    return response.data;
  } catch (error) {
    console.error(`API DELETE ${endpoint} error:`, error);
    throw new Error(error.response?.data?.message || 'API request failed');
  }
};
