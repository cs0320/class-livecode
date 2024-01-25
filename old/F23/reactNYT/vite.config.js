// This configuration file gives Vite info on running this React app.
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig(() => {
  return {
    build: {
      outDir: 'build',
    },
    plugins: [react()],
    // Development server port (same as gearup)
    server: {
      port: 8000,
    },
  };
});