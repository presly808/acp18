<template>
    <div id="app">
        <h1>My User App!</h1>
        {{ loggedUser ? loggedUser : "" }}
        <Login v-bind:disabled="isLogged"></Login>
        <button v-bind:disabled="isLogged">Log out</button>
        <AllUsers v-bind:disabled="isLogged"/>
        <AddUser v-bind:disabled="isLogged"/>
    </div>
</template>

<script>

    var loggedUser;
    var isLogged = false;
    import Login from "./vue_components/Login.vue";

    export default {
        components: {
            Login, AllUsers, AddUser
        }
    }
</script>