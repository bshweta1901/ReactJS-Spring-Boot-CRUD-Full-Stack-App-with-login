import axios from 'axios';

const AUTH_API_BASE_URL = "http://localhost:8080/api"; // Assuming a different endpoint for authentication

class AuthService {

    login(loginData) {
        return axios.post(`${AUTH_API_BASE_URL}/auth/login`, loginData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }


    
    fetchGender(payload) {
        return axios.post(`${AUTH_API_BASE_URL}/predefined/list`, payload, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }
    register(payload) {
        return axios.post(`${AUTH_API_BASE_URL}/user/register`, payload, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }
}

export default new AuthService();
