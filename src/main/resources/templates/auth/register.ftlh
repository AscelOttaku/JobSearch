<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }

        .bg-image {
            background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');
            background-size: cover;
            background-position: center bottom;
            background-repeat: no-repeat;
            height: 100vh;
            width: 100%;
        }

        .gradient-custom-3 {
            background: linear-gradient(to right, rgba(132, 250, 176, 0.5), rgba(143, 211, 244, 0.5));
        }

        .gradient-custom-4 {
            background: linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1));
        }
    </style>


    <section class="vh-100 bg-image"
             style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
        <div class="mask d-flex align-items-center h-100 gradient-custom-3">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5">Create an account</h2>

                                <form method="post" action="/users/registration">

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="name">First Name</label>
                                        <@spring.formInput "user.name", 'class="form-control form-control-lg" id="name"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="surname">Last Name</label>
                                        <@spring.formInput "user.surname", 'class="form-control form-control-lg" id="surname"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="age">Age</label>
                                        <@spring.formInput "user.age", 'class="form-control form-control-lg" id="age"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="email">Email</label>
                                        <@spring.formInput "user.email", 'class="form-control form-control-lg" id="email"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="password">Password</label>
                                        <@spring.formInput "user.password", 'class="form-control form-control-lg" id="password"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="phoneNumber">Phone Number</label>
                                        <@spring.formInput "user.phoneNumber", 'class="form-control form-control-lg" id="phoneNumber"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="accountType">Account Type</label>
                                        <select id="accountType" name="accountType" class="form-control form-control-lg"
                                                required>
                                            <option value="">Select account type</option>
                                            <option value="JobSeeker">JobSeeker</option>
                                            <option value="Employer">Employer</option>
                                        </select>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="avatar">Avatar URL (optional)</label>
                                        <@spring.formInput "user.avatar", 'class="form-control form-control-lg" id="avatar"'/>
                                        <@spring.showErrors "<br>" "error text-danger" />
                                    </div>

                                    <div class="d-flex justify-content-center">
                                        <button type="submit"
                                                class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">
                                            Register
                                        </button>
                                    </div>

                                    <p class="text-center text-muted mt-5 mb-0">
                                        Already have an account? <a href="/auth/login" class="fw-bold text-body"><u>Login
                                                here</u></a>
                                    </p>

                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</@main.layout>
