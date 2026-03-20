import axios from "axios";
import { error } from '@sveltejs/kit';
// Load environment variables from .env file for local development
import 'dotenv/config';
const API_BASE_URL = process.env.API_BASE_URL; // defined in frontend/.env

export async function load() {

    try {
        const companiesResponse = await axios({
            method: "get",
            url: `${API_BASE_URL}/api/company`,
        })
        return {
            companies: companiesResponse.data
        };
          } catch (axiosError) {
        console.log('Error loading companies:', axiosError);
    }
}


    
export const actions = {
    createCompany: async ({ request }) => {

        const data = await request.formData();
        const comp = {
            name: data.get('name'),
            email: data.get('email'),
        };

        try {
            await axios({
                method: "post",
                url: `${API_BASE_URL}/api/company`,
                headers: {
                    "Content-Type": "application/json",
                },
                data: comp,
            });

            return { success: true };
        } catch (error) {
            console.log('Error creating company:', error);
            return { success: false, error: 'Could not create company' };
        }
    }
}
