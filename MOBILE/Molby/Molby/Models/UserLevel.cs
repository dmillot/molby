using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class UserLevel
    {
        [JsonProperty("date_obtained")]
        public DateTime DateObtained { get; set; }
        public Level Level { get; set; }
    }
}
