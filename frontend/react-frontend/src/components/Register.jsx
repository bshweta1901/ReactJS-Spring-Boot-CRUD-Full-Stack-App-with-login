import React, { Component } from 'react';
import AuthService from '../services/AuthService';

class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            email: '',
            emailValid: true,
            password: '',
            confirmPassword: '',
            gender: '',
            genders: [],
            error: '',
            success: ''
        };

        this.changeHandler = this.changeHandler.bind(this);
        this.emailChangeHandler = this.emailChangeHandler.bind(this);
        this.register = this.register.bind(this);
    }

    componentDidMount() {
        this.fetchGenderOptions();
    }

    fetchGenderOptions() {
        AuthService.fetchGender({ entityType: 'GENDER' })
            .then(response => {
                this.setState({ genders: response.data });
            })
            .catch(error => {
                console.error('Error fetching genders:', error);
            });
    }

    changeHandler(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    emailChangeHandler(event) {
        const email = event.target.value;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        this.setState({ email, emailValid: emailRegex.test(email) });
    }

    validateForm() {
        const { name, email, emailValid, password, confirmPassword, gender } = this.state;

        if (!name || !email || !password || !confirmPassword || !gender) {
            this.setState({ error: 'All fields are required' });
            return false;
        }

        if (!emailValid) {
            this.setState({ error: 'Invalid email format' });
            return false;
        }

        if (password !== confirmPassword) {
            this.setState({ error: 'Passwords do not match' });
            return false;
        }

        return true;
    }
    LoginUser() {
        this.props.history.push(`/`);
    }
    register(event) {
        event.preventDefault();
        if (!this.validateForm()) {
            return;
        }

        const { name, email, password, gender } = this.state;

        const registerData = {
            name,
            email,
            password,
            gender,
            username:email,
            role: ['ROLE_USER']
        };

        AuthService.register(registerData).then(response => {
            this.setState({ success: 'Registration successful', error: '' });
            // Optionally redirect to login page
            this.props.history.push('/');
        }).catch(error => {
            console.error('Registration error:', error);
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
                            <h3 className="text-center">Register</h3>
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label>Name:</label>
                                        <input
                                            type="text"
                                            placeholder="Name"
                                            name="name"
                                            className="form-control"
                                            value={this.state.name}
                                            onChange={this.changeHandler}
                                            required
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>Email:</label>
                                        <input
                                            type="email"
                                            placeholder="Email"
                                            name="email"
                                            className={`form-control ${this.state.emailValid ? '' : 'is-invalid'}`}
                                            value={this.state.email}
                                            onChange={this.emailChangeHandler}
                                            required
                                        />
                                        {!this.state.emailValid && (
                                            <div className="invalid-feedback">Invalid email format</div>
                                        )}
                                    </div>
                                    <div className="form-group">
                                        <label>Password:</label>
                                        <input
                                            type="password"
                                            placeholder="Password"
                                            name="password"
                                            className="form-control"
                                            value={this.state.password}
                                            onChange={this.changeHandler}
                                            required
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>Confirm Password:</label>
                                        <input
                                            type="password"
                                            placeholder="Confirm Password"
                                            name="confirmPassword"
                                            className="form-control"
                                            value={this.state.confirmPassword}
                                            onChange={this.changeHandler}
                                            required
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>Gender:</label>
                                        <select
                                            name="gender"
                                            className="form-control"
                                            value={this.state.gender}
                                            onChange={this.changeHandler}
                                            required
                                        >
                                            <option value="">Select Gender</option>
                                            {this.state.genders.map(gender => (
                                                <option key={gender.value} value={gender.value}>
                                                    {gender.name}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                    {this.state.error && (
                                        <div className="alert alert-danger">
                                            {this.state.error}
                                        </div>
                                    )}
                                    {this.state.success && (
                                        <div className="alert alert-success">
                                            {this.state.success}
                                        </div>
                                    )}
                                    <button className="btn btn-primary" onClick={this.register}>Register</button>

                            
                                    <button onClick={() => this.LoginUser()} className="text-muted px-0"> Already have an account? Log in</button>
                                  
                       
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Register;
