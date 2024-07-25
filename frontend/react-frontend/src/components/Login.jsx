import React, { Component } from 'react';
import AuthService from '../services/AuthService';

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
            error: ''
        };

        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.login = this.login.bind(this);
    }
    registerUser() {
        this.props.history.push(`/register`);
    }

    changeUsernameHandler(event) {
        this.setState({ username: event.target.value });
    }

    changePasswordHandler(event) {
        this.setState({ password: event.target.value });
    }

    login(event) {
        event.preventDefault();
        const { username, password } = this.state;

        const loginData = {
            usernameOrEmail: username,
            password: password
        };

        AuthService.login(loginData).then(response => {
            // Check if response contains accessToken
            if (response.data.accessToken) {
                // Store the token in session storage
                sessionStorage.setItem('authToken', response.data.accessToken);
        
                // Optionally store the token type if needed
                sessionStorage.setItem('tokenType', response.data.tokenType);
        
                // Redirect to a protected route
                this.props.history.push('/records');
            } else {
                this.setState({ error: 'Invalid credentials' });
            }
        }).catch(error => {
            console.error('Login error:', error);
        
            // Check if the error response contains a message
            const errorMessage = error.response && error.response.data && error.response.data.message
                ? error.response.data.message
                : 'An error occurred';
        
            this.setState({ error: errorMessage });
        });
    }        

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">Login</h3>
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label>Username:</label>
                                        <input
                                            type="text"
                                            placeholder="Username"
                                            name="username"
                                            className="form-control"
                                            value={this.state.username}
                                            onChange={this.changeUsernameHandler}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>Password:</label>
                                        <input
                                            type="password"
                                            placeholder="Password"
                                            name="password"
                                            className="form-control"
                                            value={this.state.password}
                                            onChange={this.changePasswordHandler}
                                        />
                                    </div>
                                    {this.state.error && (
                                        <div className="alert alert-danger">
                                            {this.state.error}
                                        </div>
                                    )}
                                    <button className="btn btn-primary" onClick={this.login}>Login</button>

                                    <div className="d-grid justify-content-end mt-2">
                                    <button onClick={() => this.registerUser()} className="text-muted px-0">Don't have an account? Register</button>
                                  
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;
