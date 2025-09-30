import { test, expect } from '@playwright/test';
//import { express, request, response } from 'express';
import express from 'express';
import { lookupHandler, setMockGeocodingData, setMockACSData, clearMockData } from "../lookupHandler";

export async function createTestServer(port: number = 3001) {
  const app = express();
  app.use(express.json());
  
  // Set up your handler
  await lookupHandler(app);
  
  const server = app.listen(port);
  
  return {
    app,
    server,
    baseUrl: `http://localhost:${port}`
  };
}

test.describe('lookupHandler API', () => {
  let server: any;
  let baseUrl: string;

  test.beforeAll(async () => {
    // Start test server
    const testServer = await createTestServer(3001);
    server = testServer.server;
    baseUrl = testServer.baseUrl;
    
    // Set NODE_ENV to test for mock data usage
    process.env.NODE_ENV = 'test';
  });

  test.afterAll(async () => {
    // Clean up server
    if (server) {
      server.close();
    }
  });

  test.beforeEach(async () => {
    // Clear mock data before each test
    clearMockData();
  });

  test.describe('Parameter validation', () => {
    test('should return 400 for missing lat parameter', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Missing required parameters');
    });

    test('should return 400 for missing lon parameter', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Missing required parameters');
    });

    test('should return 400 for missing variables parameter', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Missing required parameters');
    });

    test('should return 400 for missing top parameter', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Missing required parameters');
    });

    test('should return 400 for missing bottom parameter', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Missing required parameters');
    });

    test('should return 400 for invalid latitude', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: 'invalid',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Invalid latitude or longitude');
    });

    test('should return 400 for invalid longitude', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: 'invalid',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Invalid latitude or longitude');
    });
  });

  test.describe('Granularity validation', () => {
    test('should return 400 for invalid top granularity', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'invalid',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Invalid granularity levels');
    });

    test('should return 400 for invalid bottom granularity', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'invalid'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Invalid granularity levels');
    });

    test('should return 400 when top level is more specific than bottom level', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'county',
          bottom: 'state'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Top level must be more general than bottom level');
    });

    test('should return 400 when top and bottom levels are the same', async ({ request }) => {
      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'state'
        }
      });

      expect(response.status()).toBe(400);
      const body = await response.json();
      expect(body.error).toContain('Top level must be more general than bottom level');
    });
  });

  test.describe('Successful requests with mock data', () => {
    test('should handle "all" granularity without geocoding', async ({ request }) => {
      const mockACSResponse = [
        ['S2501_C01_001E', 'state', 'county'],
        ['100', '01', '001']
      ];

      const expectedUrl = 'https://api.census.gov/data/2023/acs/acs5/subject?get=S2501_C01_001E&for=county:*&in=state:*';
      setMockACSData(expectedUrl, mockACSResponse);

      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'all',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(200);
      const body = await response.json();
      expect(body.data).toEqual(mockACSResponse);
      expect(body.input.Coordinates.lat).toBe(41.8240);
      expect(body.input.Coordinates.lon).toBe(-71.4128);
      expect(body.input.Variables).toEqual(['S2501_C01_001E']);
      expect(body.input.TopLevel).toBe('all');
      expect(body.input.BottomLevel).toBe('county');
      expect(body.url).toBe(expectedUrl);
    });

    test('should handle state-level granularity with geocoding', async ({ request }) => {
      const mockGeoResponse = {
        result: {
          addressMatches: [{
            geographies: {
              States: [{ STATE: '44' }],
              Counties: [{ COUNTY: '007', STATE: '44' }]
            }
          }]
        }
      };

      const mockACSResponse = [
        ['S2501_C01_001E', 'county', 'state'],
        ['150', '007', '44']
      ];

      const geoKey = '41.824,-71.4128';
      setMockGeocodingData(geoKey, mockGeoResponse);

      const expectedUrl = 'https://api.census.gov/data/2023/acs/acs5/subject?get=S2501_C01_001E&for=county:*&in=state:44';
      setMockACSData(expectedUrl, mockACSResponse);

      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(200);
      const body = await response.json();
      expect(body.data).toEqual(mockACSResponse);
      expect(body.url).toBe(expectedUrl);
    });

    test('should handle multiple variables', async ({ request }) => {
      const mockACSResponse = [
        ['S2501_C01_001E', 'S2501_C02_001E', 'state'],
        ['100', '200', '44']
      ];

      const expectedUrl = 'https://api.census.gov/data/2023/acs/acs5/subject?get=S2501_C01_001E,S2501_C02_001E&for=state:*&in=state:*';
      setMockACSData(expectedUrl, mockACSResponse);

      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E, S2501_C02_001E',
          top: 'all',
          bottom: 'state'
        }
      });

      expect(response.status()).toBe(200);
      const body = await response.json();
      expect(body.data).toEqual(mockACSResponse);
      expect(body.input.Variables).toEqual(['S2501_C01_001E', 'S2501_C02_001E']);
    });
  });

  test.describe('Error scenarios', () => {
    test('should return 404 when no geographic data found', async ({ request }) => {
      const mockGeoResponse = {
        result: {
          addressMatches: []
        }
      };

      const geoKey = '41.824,-71.4128';
      setMockGeocodingData(geoKey, mockGeoResponse);

      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'state',
          bottom: 'county'
        }
      });

      expect(response.status()).toBe(404);
      const body = await response.json();
      expect(body.error).toContain('No geographic data found');
    });
  });

  test.describe('Case sensitivity', () => {
    test('should handle uppercase granularity levels', async ({ request }) => {
      const mockACSResponse = [
        ['S2501_C01_001E', 'state'],
        ['100', '44']
      ];

      const expectedUrl = 'https://api.census.gov/data/2023/acs/acs5/subject?get=S2501_C01_001E&for=state:*&in=state:*';
      setMockACSData(expectedUrl, mockACSResponse);

      const response = await request.get(`${baseUrl}/lookup`, {
        params: {
          lat: '41.8240',
          lon: '-71.4128',
          variables: 'S2501_C01_001E',
          top: 'ALL',
          bottom: 'STATE'
        }
      });

      expect(response.status()).toBe(200);
      const body = await response.json();
      expect(body.input.TopLevel).toBe('all');
      expect(body.input.BottomLevel).toBe('state');
    });
  });
});