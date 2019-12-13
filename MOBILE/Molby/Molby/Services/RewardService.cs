using Molby.Models;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services.Interfaces
{
    public class RewardService : IRewardService
    {
        public async Task<Reward> GetRewardById(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/rewards/" + id);
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);
                    }

                    return await response.Content.ReadAsAsync<Reward>();

                }
                catch (HttpRequestException ex)
                {
                    throw new ApiException("", false, ex);
                }
                catch (UnsupportedMediaTypeException ex)
                {
                    throw new ApiException("", false, ex);
                }

            }
        }

        public async Task<List<Reward>> GetRewardsList()
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/rewards");
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);

                    }

                    return await response.Content.ReadAsAsync<List<Reward>>();

                }
                catch (HttpRequestException ex)
                {
                    throw new ApiException("", false, ex);
                }
                catch (UnsupportedMediaTypeException ex)
                {
                    throw new ApiException("", false, ex);
                }

            }
        }
    }
}
