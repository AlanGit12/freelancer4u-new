<script>
   import { enhance } from "$app/forms";
  import axios from "axios";

  let { data, form } = $props();

  // Use $derived to keep variables in sync with page data updates
  let companies = $derived(data.companies);
</script>

<!-- TODO: Implement -->

<h1>Create Companies</h1>

{#if form?.success}
  <div class="alert alert-success alert-dismissible fade show" role="alert">
    Company created successfully!
  </div>
{/if}

{#if form?.error}
  <div class="alert alert-danger alert-dismissible fade show" role="alert">
    {form.error}
  </div>
{/if}

<form class="mb-5" method="POST" action="?/createCompany" use:enhance>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="name">Name</label>
      <input
        class="form-control"
        id="name"
        name="name"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="email">Email</label>
      <input
        class="form-control"
        id="email"
        name="email"
        type="text"
        required
      />
    </div>
  </div>

  <button type="submit" class="btn btn-primary">Submit</button>
</form>



<h1>All Companies</h1>

<table class="table">
  <thead>
    <tr>
      <th>Name</th>
      <th>Email</th>
    </tr>
  </thead>
  <tbody>
    {#each companies as company}
      <tr>
        <td>{company.name}</td>
        <td>{company.email}</td>
      </tr>
    {/each}
  </tbody>
</table>