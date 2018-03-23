<template lang="html">

  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">

          <div class="modal-header">
            <slot name="header">
              default header
            </slot>
          </div>

          <div class="modal-body">
           <input type="text" placeholder="Post title" v-model="post.title"/>
           <textarea rows="10" placeholder="Post body" v-model="post.body"/>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              <button class="modal-default-button" @click="$emit('close')">
                Cancel
              </button>
              <button class="modal-default-button" @click="doValidateAndSubmit()">
                Create
              </button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
    export default {
      name: "newPost", //component name (tag name) for use in other template
      data(){
          return{
            post: {
              title:"",
              body:""
            },
            endpoint:'http://localhost:8080/post/add',
          }
      },
      methods:{
        doValidateAndSubmit() {
          fetch(this.endpoint,{
            method:"POST",
            headers: {
              "content-type":"application/json"
            },
            body: JSON.stringify(this.post),
          })
            .then( resp => {

              if (resp.ok) {
                return resp.json();
              }
              else {
                console.log("some error");
              }
            })
            .then( respAsJson => {
              console.log(respAsJson);
              this.$emit("updatePosts");
              this.$emit("close");
            })
            .catch( e => {
              console.log(e);
              this.$emit("close");
            })

        }
      }
    }
</script>

<style scoped>
  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }

  .modal-container {
    width: 300px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
  }

  .modal-header h3 {
    margin-top: 0;
    color: #42b983;
  }

  .modal-body {
    margin: 20px 0;
  }

  .modal-default-button {
    float: right;
  }

  /*
   * The following styles are auto-applied to elements with
   * transition="modal" when their visibility is toggled
   * by Vue.js.
   *
   * You can easily play with the modal transition by editing
   * these styles.
   */

  .modal-enter {
    opacity: 0;
  }

  .modal-leave-active {
    opacity: 0;
  }

  .modal-enter .modal-container,
  .modal-leave-active .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }

</style>
