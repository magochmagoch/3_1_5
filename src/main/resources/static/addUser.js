async function createNewUser(user) {
    await fetch("/api/admin",
        {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(user)})
}

async function addNewUserForm() {
    const newUserForm = document.getElementById("newUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const firstName = newUserForm.querySelector("#firstName").value.trim();
        const lastName = newUserForm.querySelector("#lastName").value.trim();
        const age = newUserForm.querySelector("#age").value.trim();
        const email = newUserForm.querySelector("#email").value.trim();
        const password = newUserForm.querySelector("#password").value.trim();

        const rolesSelected = document.getElementById("roles");

        let roles = [];
        for (let option of rolesSelected.selectedOptions) {
            if (option.value === ROLE_USER.name) {
                roles.push(ROLE_USER);
            } else if (option.value === ROLE_ADMIN.name) {
                roles.push(ROLE_ADMIN, ROLE_USER);
            }
        }

        const newUserData = {
            firstName: firstName,
            lastName: lastName,
            age: age,
            email: email,
            password: password,
            roles: roles
        };

        await createNewUser(newUserData);
        newUserForm.reset();

        document.querySelector('a#show-users-table').click();
        await fillTableOfAllUsers();
    });
}