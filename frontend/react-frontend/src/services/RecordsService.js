import axios from 'axios';

const REST_API_BASE_URL = "http://localhost:8080/api";
const apiClient = axios.create({
    baseURL: REST_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
    },
});

apiClient.interceptors.request.use((config) => {
    const token = sessionStorage.getItem("authToken"); // Directly accessing here for simplicity
    if (token) {
      console.error('Adding token to request header:', token);
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  }, (error) => {
    return Promise.reject(error);
  });
  
class RecordService {

    getRecords(payload){
        return apiClient.post(REST_API_BASE_URL+"/records/list",payload ? payload:{});
    }

    createRecords(employee){
        return apiClient.post(REST_API_BASE_URL+"/records/save", employee);
    }

    getRecordsById(employeeId){
        return apiClient.get(REST_API_BASE_URL + '/records/' + employeeId);
    }
    getCategoryList(entityType){
        return apiClient.post(REST_API_BASE_URL + '/predefined/list' , entityType?entityType:{});
    }

    updateRecords(employee){
        return apiClient.put(REST_API_BASE_URL + '/records/update' ,  employee);
    }

    deleteRecords(employeeId){
        return apiClient.delete(REST_API_BASE_URL + '/records/' + employeeId);
    }
}

export default new RecordService()